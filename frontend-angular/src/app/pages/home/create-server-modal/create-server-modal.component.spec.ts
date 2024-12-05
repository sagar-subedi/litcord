import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateServerModalComponent } from './create-server-modal.component';

describe('CreateServerModalComponent', () => {
  let component: CreateServerModalComponent;
  let fixture: ComponentFixture<CreateServerModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateServerModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateServerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
