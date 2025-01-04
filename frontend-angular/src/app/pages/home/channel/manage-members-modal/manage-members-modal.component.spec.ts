import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageMembersModalComponent } from './manage-members-modal.component';

describe('ManageMembersModalComponent', () => {
  let component: ManageMembersModalComponent;
  let fixture: ComponentFixture<ManageMembersModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageMembersModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageMembersModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
