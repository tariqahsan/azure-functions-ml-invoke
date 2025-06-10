import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OllamaAiChatComponent } from './ollama-ai-chat/ollama-ai-chat.component';
import { OpenAiChatComponent } from './open-ai-chat/open-ai-chat.component';

const routes: Routes = [
  { path: 'ollama', component: OllamaAiChatComponent}, // Define route for ollama form
  { path: 'openai', component: OpenAiChatComponent}, // Define route for openai form
  { path: '', redirectTo: '/openai', pathMatch: 'full' }, // Redirect to openai form on empty path
  // Add more routes here as needed
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
