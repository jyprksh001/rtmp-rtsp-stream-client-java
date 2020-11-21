package com.github.faucamp.simplertmp.packets

import com.github.faucamp.simplertmp.Util
import com.github.faucamp.simplertmp.io.ChunkStreamInfo
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Window Acknowledgement Size
 *
 * Also known as ServerBW ("Server bandwidth") in some RTMP implementations.
 *
 * @author francois
 */
class WindowAckSize : RtmpPacket {

  var acknowledgementWindowSize = 0

  constructor(header: RtmpHeader) : super(header)
  constructor(acknowledgementWindowSize: Int, channelInfo: ChunkStreamInfo) : super(RtmpHeader(
      if (channelInfo.canReusePrevHeaderTx(RtmpHeader.MessageType.WINDOW_ACKNOWLEDGEMENT_SIZE))
        RtmpHeader.ChunkType.TYPE_2_RELATIVE_TIMESTAMP_ONLY
      else
        RtmpHeader.ChunkType.TYPE_0_FULL, ChunkStreamInfo.RTMP_CID_PROTOCOL_CONTROL.toInt(),
      RtmpHeader.MessageType.WINDOW_ACKNOWLEDGEMENT_SIZE)) {
    this.acknowledgementWindowSize = acknowledgementWindowSize
  }

  @Throws(IOException::class)
  override fun readBody(input: InputStream) {
    acknowledgementWindowSize = Util.readUnsignedInt32(input)
  }

  @Throws(IOException::class)
  override fun writeBody(output: OutputStream) {
    Util.writeUnsignedInt32(output, acknowledgementWindowSize)
  }

  override fun array(): ByteArray = byteArrayOf()

  override fun size(): Int = 0

  override fun toString(): String {
    return "RTMP Window Acknowledgment Size"
  }
}