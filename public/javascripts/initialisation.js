/**
 * -----------------------------------------------------------------------------
 * Ce fichier contient de maniere EXHAUSTIVE les actions a mener lors de
 * l'initialisation de la vue
 * -----------------------------------------------------------------------------
 */

// On initialise la carte
map_init(DIV_CARTE_ID);

// On initialise les filtres au demarrage de l'application web
initialisationFiltres();

// On initialise les marqueurs
init_marqueur();
