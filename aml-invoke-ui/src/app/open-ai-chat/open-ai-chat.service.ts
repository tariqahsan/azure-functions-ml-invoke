import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OpenAiChatService {

  //private apiUrl = 'http://localhost:8080/api/chat'; // Spring Boot REST API
  private apiUrl = "localhost:8000/chat"; // Python REST API

  constructor(private http: HttpClient) {}

  askQuestion(prompt: string) {
    return this.http.post(this.apiUrl, { prompt }, { responseType: 'text' });
  }
}