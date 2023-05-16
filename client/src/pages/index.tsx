import { useEffect } from 'react';
import { openModal } from '../features/modalSlice';
import { useAppDispatch, useAppSelector } from '../store';
import { ModalTypes } from '../type/modal';
import { httpGetApi } from '../util';
import { fetchQuiz, fetchQuizBundleList } from '../features/mozSlice';
import Link from 'next/link';

const httpGetQuizList = async () => {
  const response = await httpGetApi('quiz');
  if (!response.ok) {
    console.error(response.statusText);
    return null;
  }

  const { quizList } = await response.json();
  return quizList;
};

async function httpGetQuizBundleList() {
  const response = await httpGetApi('quiz-bundle');
  if (!response.ok) {
    console.error(response.statusText);
    return null;
  }

  const { quizBundleList } = await response.json();
  return quizBundleList;
}

const Home = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    httpGetQuizList().then((quizList) => dispatch(fetchQuiz(quizList)));
  }, []);

  useEffect(() => {
    httpGetQuizBundleList().then((quizBundleList) => dispatch(fetchQuizBundleList(quizBundleList)));
  }, []);

  return (
    <div>
      <h1>MOZ</h1>
      <button onClick={() => dispatch(openModal(ModalTypes.QUIZ_LIST))}>퀴즈 목록</button>
      <Link href="/chat">방 목록으로</Link>
    </div>
  );
};

export default Home;
