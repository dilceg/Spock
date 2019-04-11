import org.apache.http.client.methods.HttpGet
import spock.lang.Specification

class SampleSpecTriple extends Specification {

    @groovy.transform.Canonical
    class Payload {
        final String name
    }

    interface HttpServer {
        String call(HttpGet param)
    }

    class Clazz {
        final httpServer
        Clazz(HttpServer httpServer) {
            this.httpServer = httpServer
        }

        def call() {
            httpServer.call(new HttpGet('https://google.com'))
        }
    }

    def "test simple stub"() {
        given:
        def httpServer = GroovyStub(HttpServer) {
            call(_ as HttpGet) >> "foobar"
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
        1 * httpServer.call({it.toString().contains('https://google.com')}) >> "foobar"
    }
}
