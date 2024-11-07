
****Reading Input File****

- Reading Input file is performed in InputFileReader Class under /helpers
- The InputFileReader Class reads all input files stored under /src/test/resources/inputs - Can also read more than one file
- The input car registration that matches the regex pattern are store as a List<String>

****Page Objects******

----FreeCarCheckPage----
- This is a page object class that stores all locators and corresponding method for the homepage

----ResultsPage----
- This is a page object class for the results page that fetches and stores all the relevant information as a Map<String, String>

****Searching Registration Numbers in Car Tax Check Website****

- The store list of registration numbers are searched on the Car Tax Check Website with the help of RunTestSuite Class, found under /runners
- The result page information are fetched and store as List<Map<String,String>> actualResults

****Reading Output File****
- Reading Output file is performed in OutputFileReader Class under /helpers
- The OutputFileReader Class reads all output files stored under /src/test/resources/outputs - Can also read more than one file
- The first line is read and stored as output keys in a String array
- The rest of the line are read and stored as key value pairs using constructOutput method, which is the expected output

****Comparision******

- Comparision/Assertion is performed in RunTestSuite Class

****BasePage******

- Webdriver and all browser drivers are initialised -- found under /helpers

****TestContext*****

- Contains methods to read properties file and set browser type -- found under /helpers

****test.properties****

- Base URL and browser to run the test are specified - found under src/test/resources

****TO RUN THE TEST*****

- Run the RunTestSuite.Java Class -- found under /runners