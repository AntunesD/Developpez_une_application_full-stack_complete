import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ArticlesComponent } from './pages/articlesList/articlesList.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticleDetailsComponent } from './pages/articleDetails/articleDetails.component';
import { ArticleFormComponent } from './pages/article-form/article-form.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'articles', component: ArticlesComponent },
  { path: 'articles/new', component: ArticleFormComponent },
  { path: 'articles/:id', component: ArticleDetailsComponent },
  { path: 'themes', component: ThemesComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
