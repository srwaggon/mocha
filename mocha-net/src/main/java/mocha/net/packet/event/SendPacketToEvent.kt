package mocha.net.packet.event

import mocha.net.packet.Packet

class SendPacketToEvent(packet: Packet, val playerId: Int) : SendPacketEvent(packet)
