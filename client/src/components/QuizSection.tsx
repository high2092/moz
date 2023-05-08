import { useEffect, useRef, useState } from 'react';
import { useAppSelector } from '../store';
import * as S from './QuizSection.style';
import { sendMessage } from '../../util';

export const QuizRoomMainSection = () => {
  const { socket, chatList } = useAppSelector((state) => state.moz);
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
      <S.QuizSection>문제 영역</S.QuizSection>
      <S.ChattingSection>
        <S.ChattingBox ref={chattingBoxRef}>
          {chatList.map((chat, idx) => (
            <div key={`chat-${idx}`}>{chat}</div>
          ))}
        </S.ChattingBox>
        <div>
          <input style={{ width: '100%' }} value={chattingInputValue} onChange={handleChattingInputChange} onKeyDown={handleChattingInputKeyDown} />
        </div>
      </S.ChattingSection>
    </S.QuizRoomMainSection>
  );
};
