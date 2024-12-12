import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotFound404Component } from './not-found-404.component';

describe('NotFound404Component', () => {
  let component: NotFound404Component;
  let fixture: ComponentFixture<NotFound404Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotFound404Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotFound404Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
