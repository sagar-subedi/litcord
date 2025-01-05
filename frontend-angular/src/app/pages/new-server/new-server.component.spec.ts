import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewServerComponent } from './new-server.component';

describe('NewServerComponent', () => {
  let component: NewServerComponent;
  let fixture: ComponentFixture<NewServerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewServerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
