LAVisGrafiX v0.0.1 (3/31/2015) - readme.txt

Available on:
http://lavisgrafix.cfapps.io/

What works:
1.) Two initial use cases:
Use Case 1: As an user, I want to view all assets contained in the library, so that a page with the thumbnails of all assets is displayed to me.
Use Case 2: As an user, I want to enlarge the thumbnail of an asset, so that the asset is shown to me in its original size or when this exceeds the size of the browser window, it is scaled to the size of the browser window.
2.) Complete data model is deployed on a ElephantSQL postgresql db (ddl in src/main/resources/database/pg_create_tables.sql)
3.) Assets are stored and viewed from AWS S3 storage (https://s3-eu-west-1.amazonaws.com/lavisgrafix/)
4.) AssetController and AssetRepository are implemented and tested using JUnit
5.) REST calls are implemented, e.g. 
- use http://lavisgrafix.cfapps.io/assets/ to get all assets
- use http://lavisgrafix.cfapps.io/assets/{id} to get a specific asset
6.) main.jsp displays all assets as thumbnail and on click as full size or down-scaled image
