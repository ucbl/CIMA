The interface of configuration's source code is located in the folder: \org.eclipse.om2m\org.eclipse.om2m.webapp.resourcesbrowser\src\main\resources\webapps\cima
/!\: Everything described below is relative to the path above

1) Application
	There are 4 controllers located in the folder js/controller
		a) AuthController (login page)
		b) HomeController (list of objects)
		c) DeviceController (details of objects, test objects)
		d) ProfileController (CRUD of profile and adding, edting the capabilities)
	Each of them is using a service corresponding to their use. For example : AuthController uses AuthService...
	Moreover, there is app.js (js/app.js) which declares the global configuration such as route, configuration of 3rd party (library)
2) Partials
	The folder "partials" contains the view for each specific controller. For exemple : login.html is used for AuthController...
3) Raw data
	The folder "json" contains the raw data for testing purpose (remove them later if unused anymore)
4) Tests
	There are 2 kinds of test : unit test(test/unit) and test e2e(test/e2e)
	CF README.txt in the folder "test"