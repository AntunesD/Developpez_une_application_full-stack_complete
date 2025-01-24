import { comment } from "./comment.interface";
import { Theme } from "./theme.interface";

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
  theme: Theme;
  comments: comment[];
}