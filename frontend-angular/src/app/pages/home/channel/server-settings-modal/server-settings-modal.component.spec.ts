import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerSettingsModalComponent } from './server-settings-modal.component';

describe('ServerSettingsModalComponent', () => {
  let component: ServerSettingsModalComponent;
  let fixture: ComponentFixture<ServerSettingsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServerSettingsModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServerSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
