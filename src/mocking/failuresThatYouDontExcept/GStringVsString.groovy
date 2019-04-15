import spock.lang.Specification

class GStringVsString extends Specification {
    def "failing test"() {
        /* fails with:

        java.lang.IllegalArgumentException: argument type mismatch

	at org.spockframework.mock.runtime.GroovyMockMetaClass.doInvokeMethod(GroovyMockMetaClass.java:83)
	at org.spockframework.mock.runtime.GroovyMockMetaClass.invokeMethod(GroovyMockMetaClass.java:39)
	at com.box.ghe.compliance.violation.BoxEmployeeApiClientSpec.test of fundamentals(BoxEmployeeApiClientSpec.groovy:95)

        true for: Groovy Version: 2.5.1 JVM: 1.8.0_172 Vendor: Oracle Corporation OS: Mac OS X
        Java(TM) SE Runtime Environment (build 1.8.0_172-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.172-b11, mixed mode)
        Spock framework version: /Users/dadam/.gradle/caches/modules-2/files-2.1/org.spockframework/spock-core/1.2-groovy-2.5/8a7cdbd646c4ac6be8d60c8c7a329bbe77441ea2/spock-core-1.2-groovy-2.5.jar
         */
        given:
        def constant = 'BOO'
        def httpRequest = GroovyMock(HttpUriRequest)

        when:
        httpRequest.setHeader('foo', "$constant")

        then:
        1 * httpRequest.setHeader('foo', "$constant")
    }

    def "passing test"() {
        given:
        def constant = 'BOO'
        def httpRequest = GroovyMock(HttpUriRequest)

        when:
        httpRequest.setHeader('foo', "$constant".toString())

        then:
        1 * httpRequest.setHeader('foo', "$constant")
    }
}
