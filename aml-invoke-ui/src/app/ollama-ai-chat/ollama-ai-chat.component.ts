import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-ollama-ai-chat',
  standalone: false,
  templateUrl: './ollama-ai-chat.component.html',
  styleUrl: './ollama-ai-chat.component.css'
})
export class OllamaAiChatComponent {
  prompt = new FormControl('');
  responseText = '';
  loading = false;
  apiname = "Ollama"

  constructor(private http: HttpClient) {}

  sendPrompt() {
    this.loading = true;
    const body = {
      model: 'mistral',
      prompt: this.prompt.value,
      stream: false
    };

    this.http.post<any>('http://localhost:11434/api/generate', body).subscribe({
      next: (res) => {
        this.responseText = res.response;
        this.loading = false;
      },
      error: (err) => {
        this.responseText = 'Error: ' + err.message;
        this.loading = false;
      }
    });
  }
}
  

