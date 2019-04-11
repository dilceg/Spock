import spock.lang.Specification

class SampleSpec extends Specification {

    interface HttpServer {
        String call(String param)
    }

    class Clazz {
        final httpServer
        Clazz(HttpServer httpServer) {
            this.httpServer = httpServer
        }

        def call() {
            httpServer.call('ahoy')
        }
    }

    def "test simple stub"() {
        given:
        def httpServer = GroovyStub(HttpServer) {
            call(_ as String) >> "foobar"
        }
        def clazz = new Clazz(httpServer)

        when:
        def result = clazz.call()

        then:
        result == 'foobar'
    }

    def "test simple mock"() {
        given:
        def httpServer = GroovyMock(HttpServer)
        def clazz = new Clazz(httpServer)

        when:
        clazz.call()

        then:
        1 * httpServer.call('ahoy') >> "foobar"
    }
}
