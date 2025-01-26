import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ArticlesComponent } from './pages/articlesList/articlesList.component';
import { ThemesComponent } from './pages/themesList/themesList.component';
import { ArticleDetailsComponent } from './pages/articleDetails/articleDetails.component';
import { ArticleFormComponent } from './pages/article-form/article-form.component';
import { ProfilComponent } from './pages/profil/profil.component';
import { ThemeCardComponent } from './components/theme-card/theme-card.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, HeaderComponent, RegisterComponent, LoginComponent, ArticlesComponent, ThemesComponent, ArticleDetailsComponent, ArticleFormComponent, ProfilComponent, ThemeCardComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
