import { useAppSelector } from '../store';
import { ModalType, ModalTypes } from '../type/modal';
import { AddQuizModal } from './AddQuizModal';
import { CreateQuizModal } from './CreateQuizModal';

type ModalComponents = {
  [key in ModalType]: (...params: any[]) => JSX.Element;
};

const ModalComponents: ModalComponents = {
  [ModalTypes.CREATE_QUIZ]: CreateQuizModal,
  [ModalTypes.ADD_QUIZ]: AddQuizModal,
};

const DEFAULT_MODAL_Z_INDEX = 1000;

export const ModalContainer = () => {
  const { modals } = useAppSelector((state) => state.modal);

  return (
    <>
      {modals.map((type, idx) => {
        const ModalComponent = ModalComponents[type];
        return <ModalComponent key={`modal-${type}-${idx}`} zIndex={DEFAULT_MODAL_Z_INDEX + idx} />;
      })}
    </>
  );
};