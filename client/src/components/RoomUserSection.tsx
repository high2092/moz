import * as S from './RoomUserSection.style';
import { User } from '../type/user';
import { MAX_CAPACITY } from '../../constants';

interface RoomUserSectionProps {
  users: User[]; // max length: 3
}

export const RoomUserSection = ({ users }: RoomUserSectionProps) => {
  return (
    <S.RoomUserSection>
      {users.slice(0, MAX_CAPACITY / 2).map((user) => (
        <RoomUser user={user} />
      ))}
      {Array.from({ length: MAX_CAPACITY / 2 - users.length }).map(() => (
        <S.RoomUser />
      ))}
    </S.RoomUserSection>
  );
};

interface RoomUserProps {
  user: User;
}

const RoomUser = ({ user }: RoomUserProps) => {
  return (
    <S.RoomUser>
      <div>{user}</div>
    </S.RoomUser>
  );
};
