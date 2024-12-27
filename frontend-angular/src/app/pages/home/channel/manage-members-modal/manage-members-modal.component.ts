import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-manage-members-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manage-members-modal.component.html',
  styleUrl: './manage-members-modal.component.scss'
})
export class ManageMembersModalComponent {
  @Input() members: { id: number; name: string }[] = []; // List of members
  @Input() isAdmin: boolean = false; // Whether the current user is an admin
  @Output() removeMember = new EventEmitter<number>(); // Emit member ID to delete
  @Output() close = new EventEmitter<void>(); // Close the dialog

  closeModal() {
    this.close.emit();
  }

  deleteMember(memberId: number) {
    if (confirm('Are you sure you want to remove this member?')) {
      this.removeMember.emit(memberId);
    }
  }
}
