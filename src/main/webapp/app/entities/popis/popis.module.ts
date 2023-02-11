import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PopisComponent } from './list/popis.component';
import { PopisDetailComponent } from './detail/popis-detail.component';
import { PopisUpdateComponent } from './update/popis-update.component';
import { PopisDeleteDialogComponent } from './delete/popis-delete-dialog.component';
import { PopisRoutingModule } from './route/popis-routing.module';

@NgModule({
  imports: [SharedModule, PopisRoutingModule],
  declarations: [PopisComponent, PopisDetailComponent, PopisUpdateComponent, PopisDeleteDialogComponent],
})
export class PopisModule {}
