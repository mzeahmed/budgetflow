#!/usr/bin/env bash
set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

spinner() {
  local pid="$1"
  local delay=0.1
  local spinstr='|/-\\'

  while kill -0 "$pid" 2>/dev/null; do
    local temp=${spinstr#?}
    printf " [%c]  " "$spinstr"
    spinstr=$temp${spinstr%"$temp"}
    sleep "$delay"
    printf "\b\b\b\b\b\b"
  done

  printf "    \b\b\b\b"
}

run_step() {
  local label="$1"
  shift

  printf "%b%s...%b\n" "$CYAN" "$label" "$NC"

  local log
  log="$(mktemp)"

  "$@" >"$log" 2>&1 &
  local pid=$!
  spinner "$pid"

  local status=0
  wait "$pid" || status=$?

  if [ "$status" -ne 0 ]; then
    printf "%b❌ %s échoué :%b\n" "$RED" "$label" "$NC"
    cat "$log"
    rm -f "$log"
    printf "%b❌ Commit/push annulés.%b\n" "$RED" "$NC"
    exit 1
  fi

  rm -f "$log"
  printf "%b✅ %s OK.%b\n" "$GREEN" "$label" "$NC"
}

COMMIT_MESSAGE="${1:-}"
BRANCH_NAME="${2:-}"

if [ -z "$COMMIT_MESSAGE" ]; then
  printf "%b❌ Merci de préciser le message du commit en argument.%b\n" "$RED" "$NC"
  printf "%bExemple : make commit m=\"Ajout de la gestion des transactions\" b=main%b\n" "$YELLOW" "$NC"
  exit 1
fi

if [ -z "$BRANCH_NAME" ]; then
  printf "%b❌ Merci de préciser le nom de la branche en argument.%b\n" "$RED" "$NC"
  printf "%bExemple : make commit m=\"Ajout de la gestion des transactions\" b=main%b\n" "$YELLOW" "$NC"
  exit 1
fi

REPO_ROOT="$(git rev-parse --show-toplevel)"
cd "$REPO_ROOT"

CURRENT_BRANCH="$(git rev-parse --abbrev-ref HEAD)"
if [ "$BRANCH_NAME" != "$CURRENT_BRANCH" ]; then
  printf "%b⚠️  Attention : la branche courante est '%s', mais tu as demandé '%s'.%b\n" "$YELLOW" "$CURRENT_BRANCH" "$BRANCH_NAME" "$NC"
  exit 1
fi

run_step "🔧 Compilation Java" make compile

printf "%b📦 Ajout des modifications et commit...%b\n" "$CYAN" "$NC"
git add .

if git diff --cached --quiet; then
  printf "%bℹ️  Aucun changement à committer. Rien poussé.%b\n" "$YELLOW" "$NC"
  exit 0
fi

git commit -m "$COMMIT_MESSAGE"
printf "%b✅ Commit effectué.%b\n" "$GREEN" "$NC"

printf "%b🚀 Push vers origin/%s...%b\n" "$CYAN" "$BRANCH_NAME" "$NC"
git push origin "$BRANCH_NAME"
printf "%b✅ Push effectué.%b\n" "$GREEN" "$NC"
