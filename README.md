# BudgetFlow

BudgetFlow est une application de gestion de budget personnel conçue pour être utilisée sur plusieurs plateformes : **Linux, Windows, web et mobile**.

## Objectif du projet

Permettre aux utilisateurs d'enregistrer leurs revenus et dépenses, de consulter leurs transactions et de suivre leur solde depuis différents supports, sans dupliquer les règles métier.

Le projet sert également de support à un apprentissage progressif de Java, de la conception logicielle, des tests et de Spring Boot.

## Décisions d'architecture

**Décision validée : BudgetFlow sera multiplateforme et exposera une API consommable par plusieurs clients.**

| Composant | Orientation technique | Statut |
| --- | --- | --- |
| Cœur métier | Java, indépendant des interfaces | Validé |
| API HTTP/JSON | Spring Boot REST | Orientation retenue |
| Bureau Linux et Windows | JavaFX, distribution installable avec `jpackage` | Orientation retenue |
| Web | React | Orientation retenue |
| Mobile | Technologie à définir | À décider |
| Persistance serveur | PostgreSQL | Orientation retenue |
| Tests Java | JUnit 5 | Prévu |

### Principes directeurs

1. **Un cœur métier indépendant** : les règles concernant les transactions, les montants et les budgets ne dépendent ni de Spring Boot, ni de JavaFX, ni de React.
2. **Une API réutilisable** : les clients web et mobile consomment l'API REST. Par défaut, le client bureau l'utilisera également.
3. **Pas de duplication métier** : les validations et calculs de référence sont centralisés côté Java.
4. **Séparation des responsabilités** : distinguer progressivement domaine, application, infrastructure et interfaces.
5. **Évolution incrémentale** : commencer par un domaine Java testable avant d'ajouter la persistance, l'API et les interfaces.
6. **Historique Git lisible** : un commit par chantier cohérent et validé, avec des messages de type Conventional Commits.

## Architecture cible (prévisionnelle)

```text
Applications clientes
├── Desktop : JavaFX (Linux / Windows)
├── Web     : React
└── Mobile  : à définir
           │
           ▼
       API REST
      Spring Boot
           │
           ▼
      Cœur métier
   Java indépendant
           │
           ▼
  Couche de persistance
       PostgreSQL
```

> Ce schéma est conceptuel : le domaine ne doit pas dépendre directement de PostgreSQL. L'accès aux données passera par des abstractions et des adaptateurs d'infrastructure.

### Structure de dépôt envisagée

```text
budgetflow/
├── README.md
├── budgetflow-domain/      # Entités et règles métier Java
├── budgetflow-api/         # API Spring Boot
├── budgetflow-desktop/     # Client JavaFX
├── budgetflow-web/         # Client React
└── budgetflow-mobile/      # Client mobile (à définir)
```

Cette structure représente une **cible**, pas l'état actuel du dépôt. La migration vers des modules se fera au moment opportun, après introduction d'un outil de build tel que Maven.

## État actuel

Le projet est actuellement une application Java en console. Il comprend notamment :

- `Transaction` : description, montant en `BigDecimal`, type (`INCOME` ou `EXPENSE`), validation du montant et attributs `final`.
- `TransactionType` : énumération des types de transactions.
- `Budget` : ajout de transactions, protection contre les entrées nulles, consultation via une liste non modifiable et calcul du solde.
- `Main` : scénario de démonstration en console et gestion élémentaire des exceptions.

La validation des descriptions et du type des transactions reste à terminer.

## Feuille de route

1. **Domaine Java** : validations, invariants, gestion du budget et tests unitaires.
2. **Outillage** : Maven, JUnit 5, organisation des packages et modules.
3. **Persistance** : repositories, PostgreSQL, migrations de schéma.
4. **API** : Spring Boot, endpoints REST, DTO, validation et gestion des erreurs.
5. **Desktop** : interface JavaFX et installation sur Linux et Windows.
6. **Web** : interface React connectée à l'API.
7. **Mobile** : choix technologique et client connecté à l'API.
8. **Industrialisation** : authentification, CI/CD, packaging et déploiement.

## Questions ouvertes

- Le client bureau doit-il fonctionner **hors ligne** ? Si oui, prévoir une base locale et une stratégie de synchronisation.
- Quelle technologie adopter pour le mobile ?
- Quels seront les besoins d'authentification et de gestion de plusieurs utilisateurs ?
- Quelle stratégie de distribution et de mise à jour adopter pour les applications bureau ?

## Conventions de développement

- Utiliser `BigDecimal` pour les montants financiers ; préférer les chaînes décimales lors de leur construction.
- Valider les données aux frontières des composants et préserver les invariants du domaine.
- Privilégier les objets immuables pour les données métier lorsque cela est pertinent.
- Tester le comportement métier indépendamment des interfaces.
- Ne créer un commit qu'après vérification du chantier.

Exemples de messages Git :

```text
feat: add budget balance calculation
fix: reject invalid transactions
refactor: make transactions immutable
test: cover transaction validation
```
