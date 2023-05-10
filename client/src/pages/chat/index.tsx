import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from '../../store';
import { initSocket, receiveMessage } from '../../features/mozSlice';
import { convertPayloadToChat } from '../../util';

const httpGetRoomList = async () => {
  const response = await fetch('http://localhost:8080/room', { method: 'GET', credentials: 'include' });
  const { rooms } = await response.json();
  return rooms;
};
const ChattingPage = () => {
  const router = useRouter();

  const dispatch = useAppDispatch();
  const { socket, chatList } = useAppSelector((state) => state.moz);
  const [roomList, setRoomList] = useState([]);

  useEffect(() => {
    if (socket !== null) return;
    const ws = new WebSocket('ws://localhost:8080/my-handler');
    ws.onopen = () => {
      console.log('WebSocket connection established.');
    };
    ws.onmessage = (event) => {
      console.log('Message received:', event.data);
      dispatch(receiveMessage(JSON.parse(event.data)));
    };
    ws.onclose = () => {
      console.log('WebSocket connection closed.');
    };
    dispatch(initSocket(ws));
  }, []);

  useEffect(() => {
    httpGetRoomList().then(setRoomList);
  }, []);

  useEffect(() => {
    console.log(roomList);
  }, [roomList]);

  const sendMessage = () => {
    if (socket !== null && socket.readyState === WebSocket.OPEN) {
      socket.send(
        JSON.stringify({
          type: 'A',
          body: 'Hello, server!',
        })
      );
    }
  };

  const handleRoomCreateButtonClick = async () => {
    const roomProfile = {
      name: '아무나',
      capacity: 4,
    };

    const response = await fetch('http://localhost:8080/room', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(roomProfile),
    });

    if (!response.ok) {
      return;
    }

    const id = await response.json();

    setRoomList((roomList) => roomList.concat({ ...roomProfile, id }));
  };

  const handleRoomClick = (id: number) => {
    if (!roomList.find((room) => room.id === id).requirePassword) {
      router.push(`/chat/${id}`);
    }
  };

  return (
    <div>
      <div>
        <div>
          {roomList.map(({ id, name }) => (
            <div key={id} onClick={() => handleRoomClick(id)}>
              <div>{id}</div>
              <div>{name}</div>
            </div>
          ))}
        </div>
        <button onClick={handleRoomCreateButtonClick}>방 만들기</button>
      </div>
      <div>
        <div style={{ height: '10rem', background: '#dddddd', overflow: 'scroll' }}>
          {chatList.map((chat, idx) => (
            <div key={`chat-${idx}`}>{convertPayloadToChat(chat)}</div>
          ))}
        </div>
        <button onClick={sendMessage}>Send Message</button>
      </div>
    </div>
  );
};

export default ChattingPage;
