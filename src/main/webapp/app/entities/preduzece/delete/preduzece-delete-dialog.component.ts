import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPreduzece } from '../preduzece.model';
import { PreduzeceService } from '../service/preduzece.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './preduzece-delete-dialog.component.html',
})
export class PreduzeceDeleteDialogComponent {
  preduzece?: IPreduzece;

  constructor(protected preduzeceService: PreduzeceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.preduzeceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
