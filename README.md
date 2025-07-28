| 엔티티 관계 | 다중성 | 방향성        | 부모-자식 관계     | 연관관계의 주인 |
|-------------|--------|----------------|---------------------|------------------|
| A:B         | 1:N    | B → A 단방향   | 부모: A, 자식: B    | B                |
| User : UserStatus| 1:1  | User ↔ UserStatus   | 부모: User, 자식: UserStatus| UserStatus|
| User : BinaryContent| 1:1 | User → BinaryContent| 부모: User, 자식: BinyryContent| User|
| ReadStatus : User| N:1 | ReadStatus → User | 부모: User, 자식: ReadStatus| ReadStatus|
| ReadStatus : Channel| N:1 | ReadStatus → Channel | 부모: Channel, 자식: ReadStatus| ReadStatus|
| Message : User| N:1 | Message → User | 부모: User, 자식: Message| Message|
| Message : Channel| N:1 | Message → Channel | 부모: Channel, 자식: Message| Message|
| Message : BinaryContent| 1:N | Message → BinaryContent | 부모: Message, 자식:BinaryContent| Message|
