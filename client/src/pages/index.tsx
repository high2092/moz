import { useEffect } from 'react';
import { openModal } from '../features/modalSlice';
import { useAppDispatch, useAppSelector } from '../store';
import { ModalTypes } from '../type/modal';
import { httpGetApi } from '../util';
import { fetchQuiz } from '../features/mozSlice';
import Link from 'next/link';

const fetchQuizList = async () => {
  const response = await httpGetApi('quiz');
  if (!response.ok) {
    console.error(response.statusText);
    return null;
  }

  const { quizList } = await response.json();

  return quizList;
};

const Home = () => {
  const dispatch = useAppDispatch();
  const { quizList } = useAppSelector((state) => state.moz);

  useEffect(() => {
    fetchQuizList().then((quizList) => dispatch(fetchQuiz(quizList)));
  }, []);

  const handleCreateQuizButtonClick = () => {
    dispatch(openModal(ModalTypes.CREATE_QUIZ));
  };

  return (
    <div>
      <h1>MOZ</h1>
      <div>
        <div>문제 목록</div>
        <ul>
          {quizList.map(({ id, question, answer }) => (
            <li key={id}>
              {id} {question} {answer.answer}
            </li>
          ))}
        </ul>
      </div>
      <button onClick={handleCreateQuizButtonClick}>퀴즈 생성</button>
      <Link href="/chat">방 목록으로</Link>
    </div>
  );
};

export default Home;
