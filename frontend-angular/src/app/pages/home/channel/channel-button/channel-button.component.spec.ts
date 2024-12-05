import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelButtonComponent } from './channel-button.component';

describe('ChannelButtonComponent', () => {
  let component: ChannelButtonComponent;
  let fixture: ComponentFixture<ChannelButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChannelButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChannelButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
