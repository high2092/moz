import { useAppSelector } from '../store';
import * as S from './QuizSection.style';

export const QuizRoomMainSection = () => {
  const { chatList } = useAppSelector((state) => state.moz);
  return (
    <S.QuizRoomMainSection>
      <S.QuizSection>문제 영역</S.QuizSection>
      <S.ChattingSection>
        {chatList.map((chat) => (
          <div>{chat}</div>
        ))}
      </S.ChattingSection>
    </S.QuizRoomMainSection>
  );
};
