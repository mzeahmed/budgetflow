# Configuration du projet Java
SRC_DIR := src
OUT_DIR := out
MAIN_CLASS := Main
JAVAC := javac
JAVA := java

# Couleurs affichees dans le terminal
RED := \033[0;31m
GREEN := \033[0;32m
YELLOW := \033[0;33m
BLUE := \033[0;34m
NO_COLOR := \033[0m

.DEFAULT_GOAL := help

.PHONY: help compile run clean rebuild commit

## Affiche les commandes disponibles.
help:
	@printf "$(BLUE)Commandes disponibles :$(NO_COLOR)\n"
	@printf "  $(GREEN)make compile$(NO_COLOR)  Compile les sources Java.\n"
	@printf "  $(GREEN)make run$(NO_COLOR)      Compile puis lance l'application.\n"
	@printf "  $(GREEN)make clean$(NO_COLOR)    Supprime les fichiers compiles.\n"
	@printf "  $(GREEN)make rebuild$(NO_COLOR)  Nettoie puis compile le projet.\n"
	@printf "  $(GREEN)make commit m=\"…\" b=branche$(NO_COLOR)  Compile, commit et pousse les modifications.\n"

## Compile toutes les sources Java dans le repertoire de sortie.
compile:
	@printf "$(YELLOW)Compilation des sources Java...$(NO_COLOR)\n"
	@mkdir -p $(OUT_DIR)
	@$(JAVAC) -d $(OUT_DIR) $(SRC_DIR)/*.java
	@printf "$(GREEN)Compilation terminee.$(NO_COLOR)\n"

## Compile le projet puis execute la classe principale.
run: compile
	@printf "$(YELLOW)Demarrage de l'application...$(NO_COLOR)\n"
	@$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

## Supprime les fichiers generes par la compilation.
clean:
	@printf "$(YELLOW)Nettoyage des fichiers compiles...$(NO_COLOR)\n"
	@rm -rf $(OUT_DIR)
	@printf "$(GREEN)Nettoyage termine.$(NO_COLOR)\n"

## Reconstruit le projet depuis une sortie propre.
rebuild: clean compile
	@printf "$(GREEN)Reconstruction terminee.$(NO_COLOR)\n"

## Compile, commit et pousse les modifications sur la branche indiquee.
## make commit m="message de commit" b=nom_de_la_branche
commit:
	@./shell/commit.sh "$(m)" "$(b)"
