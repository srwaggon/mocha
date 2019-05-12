package mocha.net;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

import mocha.net.exception.DisconnectedException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {

  private Connection testObject;

  @Mock
  private Socket socket;
  @Mock
  private OutputStream outputStream;
  @Mock
  private InputStream inputStream;

  @Before
  public void setUp() throws Exception {
    when(socket.getInputStream()).thenReturn(inputStream);
    when(socket.getOutputStream()).thenReturn(outputStream);

    testObject = new Connection(socket);
  }

  @Test
  public void isConnected_returnsTrue_WhenConnectionEstablished() {
    assertTrue(testObject.isConnected());
  }

  @Test
  public void isConnected_ReturnsFalse_WhenDisconnected() {
    testObject.disconnect();

    assertFalse(testObject.isConnected());
  }

  @Test
  public void hasLine_ReturnsFalse_WhenNoLineReady() {
    assertFalse(testObject.hasLine());
  }

  @Test
  public void hasLine_ReturnsTrue_WhenLineReady() throws IOException {
    when(socket.getInputStream()).thenReturn(IOUtils.toInputStream("OK\n"));
    testObject = new Connection(socket);

    assertTrue(testObject.hasLine());
  }

  @Test
  public void readLine_ReturnsLineThatWasRead() throws IOException, DisconnectedException {
    String expected = "OK";
    when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(expected));
    testObject = new Connection(socket);

    String actual = testObject.readLine();
    assertEquals(expected, actual);
  }

  @Test
  public void readLine_ReturnsLinesInOrder() throws IOException, DisconnectedException {
    when(socket.getInputStream()).thenReturn(IOUtils.toInputStream("OK1\nOK2\nOK3\n"));
    testObject = new Connection(socket);

    assertEquals("OK1", testObject.readLine());
    assertEquals("OK2", testObject.readLine());
    assertEquals("OK3", testObject.readLine());
  }

  @Test
  public void send_SendsData() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    when(socket.getOutputStream()).thenReturn(outputStream);
    testObject = new Connection(socket);
    String expected = "OK";

    testObject.send(expected);

    assertEquals(expected + System.lineSeparator(), outputStream.toString());
  }

  @Test
  public void readLine_ThrowsAnExceptionWhenTheOtherEndHasDisconnected() throws IOException {
    when(inputStream.read()).thenThrow(new NoSuchElementException());

    assertThatExceptionOfType(DisconnectedException.class)
        .isThrownBy(() -> testObject.readLine());
  }

  @Test
  public void readLine_MarksTheConnectionAsDisconnected_WhenDetected() throws IOException {
    when(inputStream.read()).thenThrow(new NoSuchElementException());
    try {
      testObject.readLine();
    } catch (DisconnectedException ignored) {

    }

    assertFalse(testObject.isConnected());
  }

}