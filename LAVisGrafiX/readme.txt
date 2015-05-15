LAVisGrafiX v0.0.2 (5/15/2015) - readme.txt

Available on:
http://lavisgrafix.cfapps.io/
http://lavisgrafix.eu-gb.mybluemix.net/

Updates in v0.0.2:
1.) Additional Use Cases:
New Use Case: As an user, I want to update the description of an asset, so that the new description is shown when the asset is displayed.
Use Case 6: As an user, I want to assign one or multiple categories to an asset, so that the categories are shown to me when the asset is displayed.
--> Comment: At the presentation layer, it is currently possible to assign a single category to an asset, while the business and data access layer are prepared to assign multiple categories to an asset.
Use Case 7: As an user, I want to assign one or multiple keywords to an asset, so that the keywords are shown to me when the asset is displayed.
--> Comment: Multiple keywords can be added to an asset as comma separated strings and will be stored in the database as distinct keywords objects.
Use Case 8: As an user, I want to assign a rating from 1 to 5 to an asset, so that the rating is shown to me when the asset is displayed.
Use Case 17: As an user, I want to delete one or multiple categories from an asset, so that the categories are removed from the asset and no longer displayed.
Use Case 18: As an user, I want to delete one or multiple keywords from an asset, so that the keywords are removed from the asset and no longer displayed.
Use Case 19: As an user, I want to delete the rating of an asset, so that the rating is removed from the asset and no longer displayed.
2.) Spring Security is enabled for the application, a customized login page (login.jsp) is created and users are required to logon to the application with their userid and password. A logout button is added to each page.
3.) Services are implemented for Asset, Category, Keyword, UserRating and User.
4.) Additional data access repositories are implemented for Category, Keyword, UserRating and User.
5.) The AssetController uses now the AssetService and is extended with function for updating assets.
6.) asset.jsp displays a single assets, the description, category, keywords and user rating, allows to modify them and to submit the request to update the asset.
7.) main.jsp and asset.jsp display basic system information such as the current user and its roles.
8.) The current version of the application has been tested with Firefox 37.0.2.


LAVisGrafiX v0.0.1 (3/31/2015) - readme.txt

Available on:
http://lavisgrafix.cfapps.io/
http://lavisgrafix.eu-gb.mybluemix.net/

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
