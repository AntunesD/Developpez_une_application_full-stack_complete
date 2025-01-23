export interface User {
  id: number;
  username: string;
}

export interface Article {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  user: User;
  themeId: number | null;
}