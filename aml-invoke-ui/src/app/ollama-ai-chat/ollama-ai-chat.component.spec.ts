import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OllamaAiChatComponent } from './ollama-ai-chat.component';

describe('OllamaAiChatComponent', () => {
  let component: OllamaAiChatComponent;
  let fixture: ComponentFixture<OllamaAiChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OllamaAiChatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OllamaAiChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
