# wp5-kb-backend-api
This is an Individual work package of Entice Knowledge Base backend API with various functionalities:

- Jena Fuseki based Knowledge Base management,
- integration with SZTAKI services,
- integration with UIBK services,
- various rest based requests that manipulates the data in the KB,
- etc.



Pre-requisites:

- Oracle Java JDK v. 1.7 or greater,
- Apache Maven v. 3.x,
- Apache Tomcat v. 7,
- Apache Jena Fuseki 2.4



How to install:

1. Install Java JDK,
2. Install Maven,
3. Install Tomcat (as service or manually),
4. Install Jena Fuseki 2.4,
5. Create a new KB dataset using Jena Fuseki WEB interface and import the ENTICE ontology "entice_ontology_M18.owl",
6. Set property files with all needed credentials (e.g. fill *.properties files) and paths to the other services,
7. Build project using the following command:
      > mvn clean install
8. Deploy the build output result *.war file into Tomcat using WEB interface or in terminal (copy *.war file into "webapps" folder),
9. Test the KB Backend API through RESTful based requests directly or through ENTICE Image portal.

In case of any issues e-mail me at: sandi.gec ( at ) fri.uni-lj.si
