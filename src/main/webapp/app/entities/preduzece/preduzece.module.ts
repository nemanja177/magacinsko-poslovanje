import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PreduzeceComponent } from './list/preduzece.component';
import { PreduzeceDetailComponent } from './detail/preduzece-detail.component';
import { PreduzeceUpdateComponent } from './update/preduzece-update.component';
import { PreduzeceDeleteDialogComponent } from './delete/preduzece-delete-dialog.component';
import { PreduzeceRoutingModule } from './route/preduzece-routing.module';

@NgModule({
  imports: [SharedModule, PreduzeceRoutingModule],
  declarations: [PreduzeceComponent, PreduzeceDetailComponent, PreduzeceUpdateComponent, PreduzeceDeleteDialogComponent],
})
export class PreduzeceModule {}
