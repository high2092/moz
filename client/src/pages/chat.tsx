import { useEffect, useState } from 'react';

const ChattingPage = () => {
  const [socket, setSocket] = useState(null);
  const [chatList, setChatList] = useState([]);

  useEffect(() => {
    const ws = new WebSocket('ws://localhost:8080/my-handler');
    ws.onopen = () => {
      console.log('WebSocket connection established.');
    };
    ws.onmessage = (event) => {
      console.log('Message received:', event.data);
      setChatList((chatList) => chatList.concat(event.data));
    };
    ws.onclose = () => {
      console.log('WebSocket connection closed.');
    };
    setSocket(ws);
  }, []);

  const sendMessage = () => {
    if (socket !== null && socket.readyState === WebSocket.OPEN) {
      socket.send('Hello, server!');
    }
  };

  return (
    <div>
      <div style={{ height: '10rem', background: '#dddddd', overflow: 'scroll' }}>
        {chatList.map((chat, idx) => (
          <div key={`chat-${idx}`}>{chat}</div>
        ))}
      </div>
      <button onClick={sendMessage}>Send Message</button>
    </div>
  );
};

export default ChattingPage;
