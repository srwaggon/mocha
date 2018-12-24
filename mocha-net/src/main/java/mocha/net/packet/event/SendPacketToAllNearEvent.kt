package mocha.net.packet.event

import mocha.game.world.Location
import mocha.net.packet.Packet

class SendPacketToAllNearEvent(packet: Packet, val location: Location, val distance: Int) : SendPacketEvent(packet)
