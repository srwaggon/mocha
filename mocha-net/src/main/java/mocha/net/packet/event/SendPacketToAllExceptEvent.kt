package mocha.net.packet.event

import mocha.net.packet.Packet

class SendPacketToAllExceptEvent(packet: Packet, val playerIds: Set<Int>) : SendPacketEvent(packet)
