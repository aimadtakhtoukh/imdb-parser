Ce projet est un scrapper pour les fichiers d'IMDB, fait comme exercice sur Akka Streams.

Le but du projet est de démontrer Akka Streams. Le focus a été sur la réduction de la mémoire utilisée et de l'espace.

A cause de la difficulté de trier des streams Akka et de l'absence de vérification, on obtient des résultats abberants, 
notamment les séries avec le plus grand nombre d'épisodes ne sont pas triés par nombre d'épisodes, et contiennent ce qui est
clairement une valeur de test pour IMDB.