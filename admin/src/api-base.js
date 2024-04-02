/**
 * URL de base de l'API à laquelle seront envoyées les requêtes.
 * Vous pouvez réécrire complètement ce fichier dans un script de CI pour que cette valeur corresponde à votre environnement de "production".
 * <strong>Attention :</strong> si vous utilisez une API externe (sur un autre serveur),
 * il faut rajouter l'origine du client dans les paramètres du filtre CORS (fichier <code>web.xml</code> du TP4).
 * @type {string}
 */
const apiBase = "http://localhost:3376";

export default apiBase;