To run drnk application:
-Run from the drnk class

View Package:
All activities that handle the view of the application are placed under Views package.
drnk class handles the main screen of the application which includes businesses of different types bars or liquor stores.
Within this class the navigation drawer is initialized.
When a cell in the Navigation Drawer is selected, a fragment is inflated inside the drnk activity.
The layouts for the bars and liquor stores are reused, and the map fragment has its own layout.

Customize Package:
The customizable package handles the customized list, and other resources to give the application an aesthetically
pleasing look.

DomainModel package:
This package handles the json object returned from an HTTP Request.
1) A static nested class called Builder inside the BusinessBuilder class whose object will be build by Builder.

2) Builder class will have exactly same set of fields as original class.
3) Builder class will expose method for adding elements (e.g. addBusiness()),each method will return same Builder
  object. Builder will be enriched with each method call.

4) Builder.build() method will copy all builder field values into actual class and return object of Business class.
5) BusinessBuilder class (class for which we are creating Builder) has a private constructor to create its object
   from build() method and to prevent outsiders to access its constructor.

Connection package:
Inside the Connection page is two classes which are LocationServices and URLReader. LocationServices handles retrieving
user's location. URLReader handles the connection to drnkMobile api.

To run tests properly:
-Right click the green "java" folder, located inside at /app/src/test/
-Run > Click the "All Tests" that has the JUnit logo, which is the Box with two arrows, NOT the Android logo.