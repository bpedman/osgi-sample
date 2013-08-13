osgi-sample
===========

Sample OSGi service that uses different methods of providing and consuming a service.
All of the submodules here provide the same service. Namely there is a bundle that
provides an interface for interacting with "Nodes" and "Entities". There is another
bundle entity-impl that provides the implementation of the interface, and finally
there is another bundle that uses the implementation of the interface to provide
a custom REST API using JAX-RS + Apache CXF.

## Submodules

### entity-blueprint

This module uses the blueprint container specification to provide and consume services.
The providing and consuming is handled by a special file found in
src/main/resources/OSGI-INF/blueprint/config.xml

### entity-service-tracker

This module uses a custom ServiceTracker to monitor service availability and starts and
stops the REST API as the service becomes available or unavailble. This module uses
a standard BundleActivator plus implements the ServiceTrackerCustomizer interface to
track service availability.

### entity-declarative-services

This module uses special annotations to create a Service Component to describe the services
that are provided and consumed by the bundles. The declarative services are actually consumed
as an XML file by the OSGi container. The annotations are used by the maven-bundle-plugin
to generate the service component XML file during the build.

## Setting Up The Container

* Download Karaf
* Start up the Karaf shell
* Run `features:install http cxf`
  * If you want to enable the Distributed OSGi stuff, you can do the following (this is not required):
      * `config:propset -p org.apache.cxf.dosgi.discovery.zookeeper zookeeper.port 2181` # This tells the local discovery client what the zookeeper server port is
      * `config:propset -p org.apache.cxf.dosgi.discovery.zookeeper.server clientPort 2181` # This tells the server what port to listen on
      * `features:chooseurl cxf-dosgi 1.5.0`
      * `features:install cxf-dosgi-discovery-distributed cxf-dosgi-zookeeper-server`
  * `install -s mvn:org.codehaus.jackson/jackson-core-asl/1.9.13`
  * `install -s mvn:org.codehaus.jackson/jackson-mapper-asl/1.9.13`
  * `install -s mvn:org.codehaus.jackson/jackson-jaxrs/1.9.13`
  * `install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-beanutils/1.8.3_1`
  * Build the code using `mvn clean package`
  * Now install the built jars into karaf using something like:
      * `install -s file:///path/to/target/entity-service-1.0.SNAPSHOT.jar`
      * And again for entity-impl and entity-rest JARs
      * You can only install the 3 bundles from one of the submodules at a time
  * Now hit [http://localhost:8181/cxf/entity?_wadl](http://localhost:8181/cxf/entity?_wadl) to check out the generated WADL definition and test out the endpoints it describes

## Distributed OSGi

All of these examples can use OSGi remote services. This means that you can have a service implementation
running in one OSGi container, and another bundle that uses the implementation running in a completely
different container and everything will work transparently. To test this out, follow the instructions
above to install the zookeeper and dosgi services. Then

* Start up another Karaf container
* Set the zookeeper port with `config:propset -p org.apache.cxf.dosgi.discovery.zookeeper zookeeper.port 2181`
* Run `features:chooseurl cxf-dosgi 1.5.0`
* `features:install cxf-dosgi-discovery-distributed` # Just the client feature, not the server

Now you can install the entity-service and entity-impl in one container, and the entity-service and
entity-rest in another container. You can hit the entity-rest endpoint in one container and it
will use the entity-rest service from the other container.
