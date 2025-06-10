// import { Component } from '@angular/core';
// import { OpenAiChatService } from './open-ai-chat.service';

// @Component({
//   selector: 'app-open-ai-chat',
//   standalone: false,
//   templateUrl: './open-ai-chat.component.html',
//   styleUrl: './open-ai-chat.component.css'
// })
// export class OpenAiChatComponent {

//   userPrompt = '';
//   response = '';

//   constructor(private chatService: OpenAiChatService) {}

//   send() {
//     this.chatService.askQuestion(this.userPrompt).subscribe(res => {
//       this.response = res;
//     });
//   }
// }




// @Component({
//   selector: 'app-chat',
//   template: `
//     <input [(ngModel)]="userPrompt" placeholder="Ask something..." />
//     <button (click)="send()">Ask</button>
//     <p *ngIf="response">Response: {{ response }}</p>
//   `,
// })


import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-open-ai-chat',
  templateUrl: './open-ai-chat.component.html',
  styleUrls: ['./open-ai-chat.component.css'],
  standalone: false
})
export class OpenAiChatComponent {
  prompt = new FormControl('');
  responseText = '';
  loading = false;
  apiname = 'DTIC Tagger';

  constructor(private http: HttpClient) {}

  sendPrompt() {
    const userPrompt = this.prompt.value?.trim();
    if (!userPrompt) return;

    this.loading = true;
    
    // this.http.post<{ response: string }>('http://localhost:8080/api/chat', { // spring boot rest api for openai
      this.http.post<{ response: string }>('http://localhost:8000/chat', { // python rest api for openai
      prompt: userPrompt
    }).subscribe({
      next: res => {
        this.responseText = res.response;
        this.loading = false;
      },
      error: err => {
        console.error('Error:', err);
        this.responseText = 'An error occurred while processing your request.';
        this.loading = false;
      }
    });
  }
}

