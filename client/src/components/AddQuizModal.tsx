import * as S from './AddQuizModal.style';
import { PreparedModalProps } from '../type/modal';
import { CenteredModal } from './Modal';
import { useAppSelector } from '../store';
import { useState } from 'react';
import { Quiz } from '../type/quiz';
import { httpPostApi } from '../util';

export const AddQuizModal = ({ zIndex }: PreparedModalProps) => {
  return <CenteredModal content={<AddQuizModalContent />} zIndex={zIndex} />;
};

function AddQuizModalContent() {
  const { quizList } = useAppSelector((state) => state.moz);
  const [addQuizList, setAddQuizList] = useState<Quiz[]>([]);

  const handleQuizClick = (quiz: Quiz) => {
    setAddQuizList((addQuizList) => addQuizList.concat(quiz));
  };

  const handleApplyButtonClick = async () => {
    const response = await httpPostApi('game/add-quiz', { quizList: addQuizList.map((quiz) => quiz.id) });
    if (!response.ok) {
      console.error(response.statusText);
      return;
    }
  };

  return (
    <S.AddQuizModal>
      <div>
        {quizList.map((quiz) => {
          const { id, consonant, answer } = quiz;
          return (
            <div key={id} onClick={() => handleQuizClick(quiz)}>
              {id} {consonant} {answer.answer}
            </div>
          );
        })}
      </div>
      <hr />
      <div style={{ display: 'flex' }}>
        {addQuizList.map(({ id }) => (
          <div key={`addQuiz-${id}`} style={{ margin: '0 2px' }}>
            {id}
          </div>
        ))}
      </div>
      <button onClick={handleApplyButtonClick}>적용</button>
    </S.AddQuizModal>
  );
}
