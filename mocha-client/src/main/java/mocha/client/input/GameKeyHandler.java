package mocha.client.input;

import mocha.client.input.event.GameKeyEvent;

interface GameKeyHandler {
  void handle(GameKeyEvent gameKeyEvent);
}
