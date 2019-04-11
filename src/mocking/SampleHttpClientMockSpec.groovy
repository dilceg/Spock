import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import spock.lang.Specification

class SampleHttpClientMockSpec extends Specification {

    def "test stub response"() {
        given:
        def httpClient = GroovyStub(HttpClient)
        def statusLine = GroovyStub(StatusLine) {
            getStatusCode() >> 111
        }
        httpClient.execute(_ as HttpUriRequest) >> GroovyStub(HttpResponse) {
            getStatusLine() >> statusLine
        }

        when:
        def result = httpClient.execute(new HttpGet("https://foobar"))

        then:
        result.getStatusLine().getStatusCode() == 111
    }

    class Clazz {
        final httpClient

        Clazz(httpClient) {
            this.httpClient = httpClient
        }

        def call() {
            httpClient.execute('https://google.com')
        }
    }

    def "test mock response"() {
        given:
        def httpClient = GroovyMock(HttpClient)
        def clazz = new Clazz(httpClient)
        def statusLine = GroovyStub(StatusLine) {
            getStatusCode() >> 111
        }
        httpClient.execute(_ as HttpUriRequest) >> GroovyStub(HttpResponse) {
            getStatusLine() >> statusLine
        }

        when:
        clazz.call()

        then:
        1 * httpClient.execute({it.toString().contains('https://google.com')}) >> ''
    }

}
