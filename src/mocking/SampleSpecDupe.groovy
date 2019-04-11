import spock.lang.Specification

class SampleSpecDupe extends Specification {

    @groovy.transform.Canonical
    class Payload {
        final String name
    }

    interface HttpServer {
        String call(Payload param)
    }

    class Clazz {
        final httpServer
        Clazz(HttpServer httpServer) {
            this.httpServer = httpServer
        }

        def call() {
            httpServer.call(new Payload('ahoy'))
        }
    }

    def "test simple stub"() {
        given:
        def httpServer = GroovyStub(HttpServer) {
            call(_ as Payload) >> "foobar"
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
        1 * httpServer.call(new Payload('ahoy')) >> "foobar"
    }
}
