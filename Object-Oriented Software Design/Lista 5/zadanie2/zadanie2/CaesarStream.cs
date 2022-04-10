using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zadanie2
{
    public class CaesarStream : Stream
    {
        private Stream _stream;
        private int _shift;
        public CaesarStream(Stream stream, int shift)
        {
            this._stream = stream;
            this._shift = shift;
        }

        public override bool CanRead => _stream.CanRead;

        public override bool CanSeek => _stream.CanSeek;

        public override bool CanWrite => _stream.CanWrite;

        public override long Length => _stream.Length;

        public override long Position { get => _stream.Position; set => _stream.Position = value; }

        public override void Flush()
        {
            _stream.Flush();
        }

        public override int Read(byte[] buffer, int offset, int count)
        {
            int result = _stream.Read(buffer, offset, count);
            for(int i = 0; i < buffer.Length; i++)
            {
                buffer[i] = (byte)(((int)buffer[i] + this._shift) % 255);
            }
            return result;
        }

        public override long Seek(long offset, SeekOrigin origin)
        {
            return _stream.Seek(offset, origin);
        }

        public override void SetLength(long value)
        {
            _stream.SetLength(value);
        }

        public override void Write(byte[] buffer, int offset, int count)
        {
            byte[] encryptedBuffer = buffer.Select((byte x) => {
                x = (byte)(((int)x + this._shift) % 255);
                return x;
            }).ToArray();
            _stream.Write(encryptedBuffer, offset, count);
        }
    }
}
