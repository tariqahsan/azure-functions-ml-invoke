import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-chat',
  standalone: false,
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {
  prompt = new FormControl('');
  responseText = '';
  loading = false;

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
