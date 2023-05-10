import { useEffect, useRef, useState } from 'react';
import { useAppDispatch, useAppSelector } from '../store';
import * as S from './QuizSection.style';
import { convertPayloadToChat, httpPostApi, sendMessage } from '../../util';
import { ready, unready } from '../features/mozSlice';

export const QuizRoomMainSection = () => {
  const { socket, chatList, isReady } = useAppSelector((state) => state.moz);
  const [chattingInputValue, setChattingInputValue] = useState('');
  const chattingBoxRef = useRef(null);

  const handleChattingInputChange = (e: React.ChangeEvent) => {
    const target = e.target as HTMLInputElement;
    setChattingInputValue(target.value);
  };

  const handleChattingInputKeyDown = (e: React.KeyboardEvent) => {
    if (e.key !== 'Enter') return;
    if (chattingInputValue.length === 0) return;
    if (e.nativeEvent.isComposing) return;

    sendMessage({ type: 'chat/local', body: chattingInputValue }, socket);

    setChattingInputValue('');
  };

  useEffect(() => {
    const chattingBox = chattingBoxRef.current;
    if (chattingBox) {
      chattingBox.scrollTop = chattingBox.scrollHeight;
    }
  }, [chatList]);

  return (
    <S.QuizRoomMainSection>
      <div style={{ background: 'green' }}>{isReady ? <UnreadyButton /> : <ReadyButton />}</div>
      <S.QuizSection>문제 영역</S.QuizSection>
      <S.ChattingSection>
        <S.ChattingBox ref={chattingBoxRef}>
          {chatList.map((chat, idx) => (
            <div key={`chat-${idx}`}>{convertPayloadToChat(chat)}</div>
          ))}
        </S.ChattingBox>
        <div>
          <input style={{ width: '100%' }} value={chattingInputValue} onChange={handleChattingInputChange} onKeyDown={handleChattingInputKeyDown} />
        </div>
      </S.ChattingSection>
    </S.QuizRoomMainSection>
  );
};

function ReadyButton() {
  const dispatch = useAppDispatch();

  const handleReadyButtonClick = async () => {
    const response = await httpPostApi('game/ready');

    if (!response.ok) {
      console.error(response.statusText);
      return;
    }

    dispatch(ready());
  };

  return <button onClick={handleReadyButtonClick}>READY</button>;
}

function UnreadyButton() {
  const dispatch = useAppDispatch();

  const handleUnreadyButtonClick = async () => {
    const response = await httpPostApi('game/unready');

    if (!response.ok) {
      console.error(response.statusText);
      return;
    }

    dispatch(unready());
  };

  return <button onClick={handleUnreadyButtonClick}>UNREADY</button>;
}
