import * as S from './QuizListModal.style';
import { openModal } from '../features/modalSlice';
import { useAppDispatch, useAppSelector } from '../store';
import { ModalTypes, PreparedModalProps } from '../type/modal';
import { CenteredModal } from './Modal';

export const QuizListModal = ({ zIndex }: PreparedModalProps) => {
  return <CenteredModal content={<QuizListModalContent />} zIndex={zIndex} />;
};

export const QuizListModalContent = () => {
  const dispatch = useAppDispatch();
  const { quizList } = useAppSelector((state) => state.moz);

  const handleCreateQuizButtonClick = () => {
    dispatch(openModal(ModalTypes.CREATE_QUIZ));
  };

  return (
    <S.QuizListModal>
      <div style={{ height: '60vh', overflow: 'scroll' }}>
        {quizList.map((quiz) => {
          const { id, question, answer } = quiz;
          return (
            <div key={id}>
              {id} {question} {answer.answer}
            </div>
          );
        })}
      </div>

      <button onClick={handleCreateQuizButtonClick}>퀴즈 생성</button>
    </S.QuizListModal>
  );
};
